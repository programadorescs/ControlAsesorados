package pe.pcs.controlasesorados.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pe.pcs.controlasesorados.databinding.ItemsBuscarBinding
import pe.pcs.controlasesorados.domain.model.Rutina

class BuscarRutinaAdapter(
    private val iOnClickListener: IOnClickListener
): ListAdapter<Rutina, BuscarRutinaAdapter.BindViewHolder>(DiffCallback) {

    interface IOnClickListener {
        fun clickItem(entidad: Rutina)
    }

    private object DiffCallback: DiffUtil.ItemCallback<Rutina>() {
        override fun areItemsTheSame(oldItem: Rutina, newItem: Rutina): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Rutina, newItem: Rutina): Boolean {
            return oldItem == newItem
        }
    }

    inner class BindViewHolder(private val binding: ItemsBuscarBinding): RecyclerView.ViewHolder(binding.root) {
        fun enlazar(entidad: Rutina) {
            binding.tvTitulo.text = entidad.descripcion

            binding.clBuscar.setOnClickListener {
                iOnClickListener.clickItem(entidad)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder {
        return BindViewHolder(
            ItemsBuscarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        val item = getItem(position)

        holder.enlazar(item)
    }

}