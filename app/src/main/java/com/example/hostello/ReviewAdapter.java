import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class HostelAdapter extends RecyclerView.Adapter<HostelAdapter.ViewHolder> {

    private Context context;
    private List<HostelModel> hostelList;

    public HostelAdapter(Context context, List<HostelModel> hostelList) {
        this.context = context;
        this.hostelList = hostelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hostel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HostelModel hostel = hostelList.get(position);

        holder.hostelName.setText(hostel.getName());
        holder.hostelAddress.setText(hostel.getAddress());
        holder.hostelImage.setImageResource(hostel.getImageRes());

        // Click on rating to open ReviewFragment
        holder.reviewRating.setOnClickListener(v -> {
            // Pass hostel info to ReviewFragment
            Bundle bundle = new Bundle();
            bundle.putString("hostelName", hostel.getName());
            bundle.putInt("hostelId", hostel.getId()); // optional

            ReviewFragment reviewFragment = new ReviewFragment();
            reviewFragment.setArguments(bundle);

            // Open fragment
            ((FragmentActivity) context).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, reviewFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return hostelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView hostelImage;
        TextView hostelName, hostelAddress, reviewRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hostelImage = itemView.findViewById(R.id.hostelImage);
            hostelName = itemView.findViewById(R.id.hostelName);
            hostelAddress = itemView.findViewById(R.id.hostelAddress);
            reviewRating = itemView.findViewById(R.id.reviewRating); // the ⭐ TextView
        }
    }
}
