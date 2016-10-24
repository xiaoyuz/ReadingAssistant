package xiaoyuz.com.readingassistant.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Date;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xiaoyuz.com.readingassistant.EventDispatcher;
import xiaoyuz.com.readingassistant.RxScreenshotDetector;
import xiaoyuz.com.readingassistant.activity.MainActivity;
import xiaoyuz.com.readingassistant.contract.AssistantContract;
import xiaoyuz.com.readingassistant.db.repository.NoteRepository;
import xiaoyuz.com.readingassistant.db.repository.local.NoteLocalDataSource;
import xiaoyuz.com.readingassistant.entity.NoteRecord;
import xiaoyuz.com.readingassistant.event.FloatWindowClickEvent;
import xiaoyuz.com.readingassistant.event.NoteRecordFileEvent;
import xiaoyuz.com.readingassistant.listener.OnScreenshotListener;
import xiaoyuz.com.readingassistant.presenter.AssistantPresenter;
import xiaoyuz.com.readingassistant.ui.widget.DraggableFrameLayout;
import xiaoyuz.com.readingassistant.utils.App;

/**
 * Created by zhangxiaoyu on 16-10-12.
 * This service running in background shall handle all reading assistance request.
 */
public class AssistantService extends Service implements OnScreenshotListener,
        AssistantContract.View {

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams;
    private DraggableFrameLayout mFloatLayout;

    private Observable mScreenshotObservable;
    private Subscriber<String> mScreenshotSubscriber;

    private AssistantContract.Presenter mPresenter;

    @Override
    public void onCreate() {
        super.onCreate();
        mPresenter = new AssistantPresenter(this,
                NoteRepository.getInstance(NoteLocalDataSource.getInstance()));
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mParams = App.getWindowParams();
        mFloatLayout = new DraggableFrameLayout(this);
        mScreenshotObservable = RxScreenshotDetector.start(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mScreenshotSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String path) {
                increaseNotifyNum();
                NoteRecord noteRecord = new NoteRecord(path, new Date().toString());
                mPresenter.addNote(noteRecord);
                EventDispatcher.post(new NoteRecordFileEvent(noteRecord,
                        NoteRecordFileEvent.Type.ADD));
            }
        };
        mScreenshotObservable.subscribe(mScreenshotSubscriber);

        mPresenter.loadNoteList();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFlowWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeAssistant();
        mScreenshotSubscriber.unsubscribe();
    }

    @Override
    public void onScreenshotTaken(Uri uri) {
        Toast.makeText(this, uri.getPath(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(AssistantContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void increaseNotifyNum() {
        mFloatLayout.increaseNotifyNum();
    }

    // FIXME: Fix the float window size.
    @Override
    public void showFlowWindow() {
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mParams.format = PixelFormat.RGBA_8888;
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.x = 300;
        mParams.y = 300;

        mFloatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoteList();
                EventDispatcher.post(new FloatWindowClickEvent());
            }
        });
        mWindowManager.addView(mFloatLayout, mParams);
    }

    @Override
    public void removeAssistant() {
        mWindowManager.removeView(mFloatLayout);
    }

    @Override
    public void showNoteList() {
        mFloatLayout.clearNotifyNum();
        Intent intent = new Intent(AssistantService.this, MainActivity.class);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setAction(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        App.getContext().startActivity(intent);
    }
}
