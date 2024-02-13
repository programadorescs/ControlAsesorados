package pe.pcs.controlasesorados.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pe.pcs.controlasesorados.databinding.ItemsUnaFilaBinding
import pe.pcs.controlasesorados.domain.model.Maquina

class MaquinaAdapter(
    private val iOnClickListener: IOnClickListener
): ListAdapter<Maquina, MaquinaAdapter.BindViewHolder>(DiffCallback) {

    interface IOnClickListener {
        fun clickEditar(entidad: Maquina)
        fun clickEliminar(entidad: Maquina)
    }

    private object DiffCallback: DiffUtil.ItemCallback<Maquina>() {
        override fun areItemsTheSame(oldItem: Maquina, newItem: Maquina): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Maquina, newItem: Maquina): Boolean {
            return oldItem == newItem
        }
    }

    inner class BindViewHolder(private val binding: ItemsUnaFilaBinding): RecyclerView.ViewHolder(binding.root) {
        fun enlazar(entidad: Maquina) {
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