package com.mmc.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.mmc.R;
import com.mmc.models.*;
import com.mmc.repositories.OrderRepository;
import com.mmc.services.OrderService;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.DecimalFormat;
import java.util.List;

public class CourseDetailActivity extends AppCompatActivity {
    Course course;
    Account loggedInUser;
    List<Order> allOrdersOfUser;
    ImageButton btnGoBack, btnFavorite;
    ImageView ivCourseImage;
    CircleImageView ivMentorImage;
    TextView tvSubjectName, tvCourseName, tvMentorName, tvCourseRating;
    DecimalFormat decimalFormatter;
    Button btnBuy;
    OrderService orderService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        initComponents();
        initListeners();
    }

    private void initComponents() {
        orderService = OrderRepository.getOrderService();
        decimalFormatter = new DecimalFormat("0.#");
        ivCourseImage = findViewById(R.id.ivCourseImage);
        ivMentorImage = findViewById(R.id.ivMentorImage);
        tvSubjectName = findViewById(R.id.tvSubjectName);
        tvCourseName = findViewById(R.id.tvCourseName);
        tvMentorName = findViewById(R.id.tvMentorName);
        tvCourseRating = findViewById(R.id.tvCourseRating);

        btnGoBack = findViewById(R.id.btnGoBack);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnBuy = findViewById(R.id.btnBuy);

        if (getIntent() != null) {
            course = (Course) getIntent().getSerializableExtra("COURSE");
            loggedInUser = (Account) getIntent().getSerializableExtra("LOGGED_IN_USER");
            allOrdersOfUser = (List<Order>) getIntent().getSerializableExtra("ORDERS");
        }

        tvSubjectName.setText(course.getSubject().getName());
        tvCourseName.setText(course.getName());
        tvMentorName.setText(course.getMentor().getFullName());
        tvCourseRating.setText(decimalFormatter.format(course.getTotalRating()));

        if (isCourseAlreadyBought(course.getId())) {
            btnBuy.setText("Already Bought");
        } else {
            btnBuy.setText("Buy Now For $" + decimalFormatter.format(course.getPrice()));
        }
        if (course.getMentor().getImageUrl() != null) {
            Glide.with(this).load(course.getMentor().getImageUrl()).into(ivMentorImage);
        }
        if (course.getImageUrl() != null) {
            Glide.with(this).load(course.getImageUrl()).into(ivCourseImage);
        }

    }

    private void initListeners() {
        btnGoBack.setOnClickListener(v -> onBackPressed());
        if (!isCourseAlreadyBought(course.getId())) {
            btnBuy.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to buy the course " + course.getName() + "?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    CreateOrderRequest createOrderRequest = new CreateOrderRequest(loggedInUser.getId(), course.getId());
                    createOrder(createOrderRequest);
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.show();
            });
        }
    }

    private void createOrder(CreateOrderRequest createOrderRequest) {
        orderService
                .createOrder(createOrderRequest)
                .enqueue(new Callback<CreateOrderResponse>() {
                    @Override
                    public void onResponse(Call<CreateOrderResponse> call, Response<CreateOrderResponse> response) {
                        if (response.code() == 200
                                && response.body() != null
                                && response.body().getStatus() != null
                                && response.body().getStatus().isSuccess()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CourseDetailActivity.this);
                            builder.setTitle("Success");
                            builder.setMessage("You have successfully bought the course " + course.getName() + ".");
                            builder.setPositiveButton("OK", (dialog, which) -> {
                                dialog.dismiss();
                                onBackPressed();
                            });
                            builder.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateOrderResponse> call, Throwable t) {

                    }
                });
    }

    private boolean isCourseAlreadyBought(long courseId) {
        for (Order o : allOrdersOfUser) {
            if (o.getCourseId() == courseId) {
                return true;
            }
        }
        return false;
    }
}