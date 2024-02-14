package pe.pcs.controlasesorados.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pe.pcs.controlasesorados.databinding.ItemsAsesoradoBinding
import pe.pcs.controlasesorados.domain.model.Asesorado

class AsesoradoAdapter(
    private val iOnClickListener: IOnClickListener
) : ListAdapter<Asesorado, AsesoradoAdapter.BindViewHolder>(DiffCallback) {

    interface IOnClickListener {
        fun clickEditar(entidad: Asesorado)
        fun clickEliminar(entidad: Asesorado)
        fun clickObjetivo(entidad: Asesorado)
    }

    private object DiffCallback : DiffUtil.ItemCallback<Asesorado>() {
        override fun areItemsTheSame(
            oldItem: Asesorado,
            newItem: Asesorado
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Asesorado,
            newItem: Asesorado
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class BindViewHolder(private val binding: ItemsAsesoradoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun enlazar(entidad: Asesorado) {
            binding.tvTitulo.text = entidad.nombre
            binding.tvFecnac.text = entidad.fecnac
            binding.tvDni.text = entidad.dni
            binding.tvSexo.text = entidad.sexo
            binding.tvTelefono.text = entidad.telefono

            binding.btEditar.setOnClickListener {
                iOnClickListener.clickEditar(entidad)
            }

            binding.btQuitar.setOnClickListener {
                iOnClickListener.clickEliminar(entidad)
            }

            binding.btObjetivo.setOnClickListener {
                iOnClickListener.clickObjetivo(entidad)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder {
        return BindViewHolder(
            ItemsAsesoradoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        val item = getItem(position)

        holder.enlazar(item)
    }
}