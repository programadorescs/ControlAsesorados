package pe.pcs.controlasesorados.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pe.pcs.controlasesorados.databinding.ItemsObjetivoBinding
import pe.pcs.controlasesorados.domain.model.Objetivo

class ObjetivoAdapter(
    private val iOnClickListener: IOnClickListener
) : ListAdapter<Objetivo, ObjetivoAdapter.BindViewHolder>(DiffCallback) {

    interface IOnClickListener {
        fun clickControl(entidad: Objetivo)
        fun clickFinalizar(entidad: Objetivo)
        fun clickEjercicio(entidad: Objetivo)
    }

    private object DiffCallback : DiffUtil.ItemCallback<Objetivo>() {
        override fun areItemsTheSame(
            oldItem: Objetivo,
            newItem: Objetivo
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Objetivo,
            newItem: Objetivo
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class BindViewHolder(private val binding: ItemsObjetivoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun enlazar(entidad: Objetivo) {
            binding.tvTitulo.text = entidad.descripcion
            binding.tvFecnac.text = entidad.fecha
            binding.tvObs.text = entidad.obs
            binding.tvEstado.text = entidad.estado

            binding.btControl.setOnClickListener {
                iOnClickListener.clickControl(entidad)
            }

            binding.btFinalizar.setOnClickListener {
                iOnClickListener.clickFinalizar(entidad)
            }

            binding.btEjercicio.setOnClickListener {
                iOnClickListener.clickEjercicio(entidad)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder {
        return BindViewHolder(
            ItemsObjetivoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        val item = getItem(position)

        holder.enlazar(item)
    }
}