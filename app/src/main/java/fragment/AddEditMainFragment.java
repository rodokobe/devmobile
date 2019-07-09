package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.projeto.academicplanner.R;

public class AddEditMainFragment extends Fragment {

    public AddEditMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View addEditMain = inflater.inflate(R.layout.fragment_add_edit_main, container, false);

        return addEditMain;
    }

}
