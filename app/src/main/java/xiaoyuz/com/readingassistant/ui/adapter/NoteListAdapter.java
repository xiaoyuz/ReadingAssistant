package xiaoyuz.com.readingassistant.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import xiaoyuz.com.readingassistant.R;
import xiaoyuz.com.readingassistant.entity.NoteRecord;
import xiaoyuz.com.readingassistant.utils.App;

/**
 * Created by zhangxiaoyu on 16-10-18.
 */
public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder> {

    class NoteListViewHolder extends ViewHolder {

        ImageView mImageView;
        TextView mTextView;

        public NoteListViewHolder(View view){
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.image);
            mTextView = (TextView) view.findViewById(R.id.time);
        }
    }

    private List<NoteRecord> mNoteRecordList;


    public NoteListAdapter(List<NoteRecord> noteRecordList) {
        mNoteRecordList = noteRecordList;
    }

    @Override
    public NoteListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item,
                parent,false);
        return new NoteListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteListViewHolder holder, int position) {
        holder.mTextView.setText(mNoteRecordList.get(position).getTime());
        Glide.with(App.getContext())
                .load(mNoteRecordList.get(position).getFilePath()).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mNoteRecordList.size();
    }

    public void setNoteRecordList(List<NoteRecord> noteRecordList) {
        mNoteRecordList = noteRecordList;
    }
}
