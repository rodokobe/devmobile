package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.projeto.academicplanner.R;

public class AddStudentFragment extends Fragment {

    public AddStudentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View addStudent = inflater.inflate(R.layout.fragment_add_student, container, false);

        return addStudent;

    }

}
