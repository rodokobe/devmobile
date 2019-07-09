package Interface;

import java.util.List;

import model.University;

public interface IFirebaseLoadDoneUniversity {

    void onFireBaseLoadSuccess(List<University> universitiesList);
    void onFireBaseLoadFailed(String message);
}
