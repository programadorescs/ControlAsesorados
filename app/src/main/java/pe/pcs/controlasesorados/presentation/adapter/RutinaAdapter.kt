package pe.pcs.controlasesorados.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pe.pcs.controlasesorados.databinding.ItemsUnaFilaBinding
import pe.pcs.controlasesorados.domain.model.Rutina

class RutinaAdapter(
    private val iOnClickListener: IOnClickListener
): ListAdapter<Rutina, RutinaAdapter.BindViewHolder>(DiffCallback) {

    interface IOnClickListener {
        fun clickEditar(entidad: Rutina)
        fun clickEliminar(entidad: Rutina)
    }

    private object DiffCallback: DiffUtil.ItemCallback<Rutina>() {
        override fun areItemsTheSame(oldItem: Rutina, newItem: Rutina): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Rutina, newItem: Rutina): Boolean {
            return oldItem == newItem
        }
    }

    inner class BindViewHolder(private val binding: ItemsUnaFilaBinding): RecyclerView.ViewHolder(binding.root) {
        fun enlazar(entidad: Rutina) {
            binding.tvTitulo.text = entidad.descripcion

            binding.ibEditar.setOnClickListener {
                iOnClickListener.clickEditar(entidad)
            }

            binding.ibEliminar.setOnClickListener {
                iOnClickListener.clickEliminar(entidad)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder {
        return BindViewHolder(
            ItemsUnaFilaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        val item = getItem(position)

        holder.enlazar(item)
    }
}