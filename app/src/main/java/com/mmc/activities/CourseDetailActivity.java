package com.mmc.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.mmc.R;
import com.mmc.models.Course;
import de.hdodenhof.circleimageview.CircleImageView;

import java.text.DecimalFormat;

public class CourseDetailActivity extends AppCompatActivity {
    Course course;
    ImageButton btnGoBack;
    ImageView ivCourseImage;
    CircleImageView ivMentorImage;
    TextView tvSubjectName, tvCourseName, tvMentorName, tvCourseRating;

    Button btnBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        initComponents();
    }

    private void initComponents() {
        DecimalFormat decimalFormatter = new DecimalFormat("0.#");

        btnGoBack = findViewById(R.id.btnGoBack);
        ivCourseImage = findViewById(R.id.ivCourseImage);
        ivMentorImage = findViewById(R.id.ivMentorImage);
        tvSubjectName = findViewById(R.id.tvSubjectName);
        tvCourseName = findViewById(R.id.tvCourseName);
        tvMentorName = findViewById(R.id.tvMentorName);
        tvCourseRating = findViewById(R.id.tvCourseRating);
        btnBuy = findViewById(R.id.btnBuy);

        if (getIntent() != null) {
            course = (Course) getIntent().getSerializableExtra("COURSE");
        }

        btnGoBack.setOnClickListener(v -> onBackPressed());
        tvSubjectName.setText(course.getSubject().getName());
        tvCourseName.setText(course.getName());
        tvMentorName.setText(course.getMentor().getFullName());
        tvCourseRating.setText(decimalFormatter.format(course.getTotalRating()));
        btnBuy.setText("Buy Now For $" + decimalFormatter.format(course.getPrice()));
        if (course.getMentor().getImageUrl() != null) {
            Glide.with(this).load(course.getMentor().getImageUrl()).into(ivMentorImage);
        }
        if (course.getImageUrl() != null) {
            Glide.with(this).load(course.getImageUrl()).into(ivCourseImage);
        }

    }
}