package Interface;

import java.util.List;

import model.Course;

public interface IFirebaseLoadDoneCourse {

    void onFireBaseLoadSuccess(List<Course> coursesList);
    void onFireBaseLoadFailed(String message);
}
