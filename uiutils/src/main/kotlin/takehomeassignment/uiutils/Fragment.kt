package takehomeassignment.uiutils

import android.app.Application
import androidx.fragment.app.Fragment

val Fragment.activityApplication: Application get() = requireActivity().application