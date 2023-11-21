package com.example.pruebafinal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull MainModel model) {
        holder.codigo.setText(model.getCodigo());
        holder.nombre.setText(model.getNombre());
        holder.tipo.setText(model.getTipo());
        holder.marca.setText(model.getMarca());
        holder.cantidad.setText(String.valueOf(model.getCantidad()));

        holder.editar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.nombre.getContext())
                        .setContentHolder(new ViewHolder(R.layout.ventana_emergente))
                        .setExpanded(true, 1500)
                        .create();
                View view = dialogPlus.getHolderView();
                EditText nombre = view.findViewById(R.id.nombretxt);
                EditText codigo = view.findViewById(R.id.codigotxt);
                EditText tipo = view.findViewById(R.id.tipotxt);
                EditText marca = view.findViewById(R.id.marcatxt);
                EditText cantidad = view.findViewById(R.id.cantidadtxt);

                Button actualizar = view.findViewById(R.id.btn_actualizar);

                codigo.setText(model.getCodigo());
                nombre.setText(model.getNombre());
                tipo.setText(model.getTipo());
                marca.setText(model.getMarca());
                cantidad.setText(String.valueOf(model.getCantidad()));

                dialogPlus.show();

                actualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("Codigo", codigo.getText().toString());
                        map.put("Nombre", nombre.getText().toString());
                        map.put("Tipo", tipo.getText().toString());
                        map.put("Marca", marca.getText().toString());
                        map.put("Cantidad", Long.parseLong(cantidad.getText().toString()));

                        FirebaseDatabase.getInstance().getReference().child("Inventario")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>(){
                                    @Override
                                    public void onSuccess(Void unused){
                                        Toast.makeText(holder.nombre.getContext(),"Actualizacion Correcta",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }//
                                }).addOnFailureListener(new OnFailureListener(){
                                    @Override
                                    public void onFailure(@NonNull Exception e){
                                        Toast.makeText(holder.nombre.getContext(), "Error en la actualizacion", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });
        holder.eliminar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.nombre.getContext());
                builder.setTitle("Estas seguro de Eliminar");
                builder.setMessage("Eliminado");

                builder.setPositiveButton("Eliminado", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Inventario")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        Toast.makeText(holder.nombre.getContext(),"Cancelar",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, tipo, marca, cantidad, codigo;
        Button editar, eliminar;

        public myViewHolder(@NonNull View itemView){
            super(itemView);

            nombre = itemView.findViewById(R.id.nombretxt);
            tipo = itemView.findViewById(R.id.tipotxt);
            marca = itemView.findViewById(R.id.marcatxt);
            cantidad = itemView.findViewById(R.id.cantidadtxt);
            codigo = itemView.findViewById(R.id.codigotxt);

            editar = itemView.findViewById(R.id.btn_edit);
            eliminar = itemView.findViewById(R.id.btn_eliminar);
        }

    }

}
