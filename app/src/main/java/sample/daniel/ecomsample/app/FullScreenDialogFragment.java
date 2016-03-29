package sample.daniel.ecomsample.app;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Daniel on 16/3/29.
 */
public class FullScreenDialogFragment extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenTransparentDialog);
    }


}
