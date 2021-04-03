package takehomeassignment.uiutils

import androidx.recyclerview.widget.RecyclerView

val RecyclerView.Adapter<*>.isEmpty get() = itemCount == 0
