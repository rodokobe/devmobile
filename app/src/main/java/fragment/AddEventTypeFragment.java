package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.projeto.academicplanner.R;

public class AddEventTypeFragment extends Fragment {

    public AddEventTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View addEventType = inflater.inflate(R.layout.fragment_add_event_type, container, false);

        return addEventType;

    }

}
