package com.mmc.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.mmc.R;
import com.mmc.activities.CourseDetailActivity;
import com.mmc.models.Course;
import de.hdodenhof.circleimageview.CircleImageView;

import java.text.DecimalFormat;
import java.util.List;

public class HomeCourseAdapter extends RecyclerView.Adapter<HomeCourseAdapter.ViewHolder> {

    private Context context;
    private List<Course> courses;

    public HomeCourseAdapter(Context context, List<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DecimalFormat decimalFormatter = new DecimalFormat("0.#");

        Course course = courses.get(position);

        holder.tvCourseName.setText(course.getName());
        holder.tvMentees.setText(String.valueOf(course.getCurrentNumberMentee()));
        holder.tvCoursePrice.setText("$" + decimalFormatter.format(course.getPrice()));
        holder.tvRating.setText(decimalFormatter.format(course.getTotalRating()));
        holder.tvMentorFullName.setText(course.getMentor().getFullName());
        holder.tvSubjectName.setText(course.getSubject().getName());

        if (course.getMentor().getImageUrl() != null) {
            Glide.with(context).load(course.getMentor().getImageUrl()).into(holder.ivMentorImage);
        }
        if (course.getImageUrl() != null) {
            Glide.with(context).load(course.getImageUrl()).into(holder.ivCourseImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CourseDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("COURSE", course);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCourseImage;
        CircleImageView ivMentorImage;
        TextView tvSubjectName;
        TextView tvMentorFullName;
        TextView tvCourseName;
        TextView tvRating;
        TextView tvMentees;
        TextView tvCoursePrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCourseImage = itemView.findViewById(R.id.ivCourseImage);
            ivMentorImage = itemView.findViewById(R.id.ivMentorImage);
            tvSubjectName = itemView.findViewById(R.id.tvSubjectName);
            tvMentorFullName = itemView.findViewById(R.id.tvMentorFullName);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvMentees = itemView.findViewById(R.id.tvMentees);
            tvCoursePrice = itemView.findViewById(R.id.tvCoursePrice);
        }
    }
}
