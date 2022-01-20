package fr.nantes.iut.tptan.presentation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import fr.nantes.iut.tptan.R;
import fr.nantes.iut.tptan.data.entity.Arret;
import fr.nantes.iut.tptan.data.entity.ListArret;
import fr.nantes.iut.tptan.data.entity.NumLigne;

public class ProximityStopAdapter extends RecyclerView.Adapter<ProximityStopAdapter.ViewHolder> {

    private List<Arret> mArrets ;
    private ArretClickListener mArretClickListener;

    public ProximityStopAdapter(List<Arret> arrets, ArretClickListener arretClickListener ) {
        this.mArrets = arrets ;
        this.mArretClickListener = arretClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_proximity_stop_listitem, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Arret arret = this.mArrets.get(position);
        holder.mCodeLieuTextView.setText(arret.getLibelle());
        if ( arret.getDistance() != null ) {
            holder.mDistanceTextView.setText(arret.getDistance());
        } else {
            holder.mDistanceTextView.setText("Inconnu");
        }

        holder.mLigneLayout.removeAllViews();
        ImageView uiArretNumLigne;
        int imageSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, holder.itemView.getContext().getResources().getDisplayMetrics());
        int imagePadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,2, holder.itemView.getContext().getResources().getDisplayMetrics());
        if ( arret.getLignes() != null ) {
            for (NumLigne numLigne : arret.getLignes()) {
                uiArretNumLigne = new ImageView(holder.itemView.getContext());
                uiArretNumLigne.setImageResource(numLigne.getNumLigneImageResourceId(holder.itemView.getContext()));
                uiArretNumLigne.setAdjustViewBounds(true);
                uiArretNumLigne.setMaxHeight(imageSize);
                uiArretNumLigne.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
                holder.mLigneLayout.addView(uiArretNumLigne);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArretClickListener.arretClicked(arret);
            }
        });
        holder.mIconImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mArretClickListener.iconLongClick(arret);
                return false;
            }
        });

        File imageDir = new File(holder.itemView.getContext().getFilesDir(), "images");
        File photoFile = new File(imageDir, arret.getCodeLieu() + ".jpg");
        if ( photoFile.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath(), options);
            holder.mIconImageView.setImageBitmap(bitmap);
        }
        else {
            holder.mIconImageView.setImageResource(R.drawable.icon_stop);
        }
    }

    @Override
    public int getItemCount() {
        return mArrets.size();
    }


    public void updateListArrets( ListArret listArrets) {
        this.mArrets.clear();
        this.mArrets.addAll(listArrets);
        this.notifyDataSetChanged();
    }


    public interface ArretClickListener {
        void arretClicked(Arret item);
        void iconLongClick(Arret item);
    }

    /**
     *
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mIconImageView;
        public TextView mCodeLieuTextView;
        public TextView mDistanceTextView;
        public LinearLayout mLigneLayout;

        public ViewHolder(View view) {
            super(view);
            mCodeLieuTextView = view.findViewById(R.id.arretproche_libellelieu);
            mDistanceTextView = view.findViewById(R.id.arretproche_distance);
            mLigneLayout  = view.findViewById(R.id.arretproche_lignes);
            mIconImageView = view.findViewById(R.id.arretproche_icon);
        }
    }
}
