package pe.pcs.controlasesorados.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pe.pcs.controlasesorados.databinding.ItemsEjercicioBinding
import pe.pcs.controlasesorados.domain.model.report.ReporteEjercicioModel

class EjercicioAdapter(
    private val iOnClickListener: IOnClickListener
) : ListAdapter<ReporteEjercicioModel, EjercicioAdapter.BindViewHolder>(DiffCallback) {

    interface IOnClickListener {
        fun clickEditar(entidad: ReporteEjercicioModel)
        fun clickEliminar(entidad: ReporteEjercicioModel)
    }

    private object DiffCallback : DiffUtil.ItemCallback<ReporteEjercicioModel>() {
        override fun areItemsTheSame(
            oldItem: ReporteEjercicioModel,
            newItem: ReporteEjercicioModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ReporteEjercicioModel,
            newItem: ReporteEjercicioModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class BindViewHolder(private val binding: ItemsEjercicioBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun enlazar(entidad: ReporteEjercicioModel) {
            binding.tvTitulo.text = entidad.descripcion
            binding.tvMaquina.text = entidad.maquina
            binding.tvRutina.text = entidad.rutina

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
            ItemsEjercicioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        val item = getItem(position)

        holder.enlazar(item)
    }
}