package com.example.appreforma4;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etCodigo, etProducto, etPrecio, etCantidad;
    private RecyclerView recyclerView;
    private ProformaAdapter adapter;
    private List<ProformaItem> proformaList;
    private TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inicializar vistas
        etCodigo = findViewById(R.id.etCodigo);
        etProducto = findViewById(R.id.etProducto);
        etPrecio = findViewById(R.id.etPrecio);
        etCantidad = findViewById(R.id.etCantidad);
        recyclerView = findViewById(R.id.recyclerViewProforma);
        tvTotal = findViewById(R.id.tvTotal);

        // 2. Configurar RecyclerView
        proformaList = new ArrayList<>();
        adapter = new ProformaAdapter(proformaList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // 3. Configurar Eventos de Botones
        findViewById(R.id.btnNuevo).setOnClickListener(v -> limpiarCampos());
        findViewById(R.id.btnGrabar).setOnClickListener(v -> grabarItem());
        findViewById(R.id.btnActualizar).setOnClickListener(v -> actualizarItem());
        findViewById(R.id.btnEliminar).setOnClickListener(v -> eliminarItem());
    }

    private void limpiarCampos() {
        etCodigo.setText("");
        etProducto.setText("");
        etPrecio.setText("");
        etCantidad.setText("");
        etCodigo.requestFocus();
    }

    private void grabarItem() {
        String codigo = etCodigo.getText().toString().trim();
        String producto = etProducto.getText().toString().trim();

        if (codigo.isEmpty() || producto.isEmpty()) {
            Toast.makeText(this, "Código y Producto son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        double precio = 0;
        int cantidad = 0;
        try {
            precio = Double.parseDouble(etPrecio.getText().toString());
            cantidad = Integer.parseInt(etCantidad.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ingrese precios y cantidades válidas", Toast.LENGTH_SHORT).show();
            return;
        }

        double total = precio * cantidad;
        ProformaItem item = new ProformaItem(codigo, producto, precio, cantidad, total);

        proformaList.add(item);
        adapter.notifyItemInserted(proformaList.size() - 1);

        actualizarTotalGeneral();
        limpiarCampos();
        Toast.makeText(this, "Producto grabado exitosamente", Toast.LENGTH_SHORT).show();
    }

    private void actualizarItem() {
        String codigo = etCodigo.getText().toString().trim();
        if (codigo.isEmpty()) {
            Toast.makeText(this, "Ingrese un código para actualizar", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean encontrado = false;
        for (int i = 0; i < proformaList.size(); i++) {
            if (proformaList.get(i).getCodigo().equals(codigo)) {
                String producto = etProducto.getText().toString().trim();
                double precio = 0;
                int cantidad = 0;

                try {
                    precio = Double.parseDouble(etPrecio.getText().toString());
                    cantidad = Integer.parseInt(etCantidad.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Datos numéricos inválidos", Toast.LENGTH_SHORT).show();
                    return;
                }

                double total = precio * cantidad;
                ProformaItem itemActualizado = new ProformaItem(codigo, producto, precio, cantidad, total);

                proformaList.set(i, itemActualizado);
                adapter.notifyItemChanged(i);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            actualizarTotalGeneral();
            limpiarCampos();
            Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Código no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarItem() {
        String codigo = etCodigo.getText().toString().trim();
        if (codigo.isEmpty()) {
            Toast.makeText(this, "Ingrese un código para eliminar", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean eliminado = false;
        for (int i = 0; i < proformaList.size(); i++) {
            if (proformaList.get(i).getCodigo().equals(codigo)) {
                proformaList.remove(i);
                adapter.notifyItemRemoved(i);
                eliminado = true;
                break;
            }
        }

        if (eliminado) {
            actualizarTotalGeneral();
            limpiarCampos();
            Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Código no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    protected void actualizarTotalGeneral() {
        double totalGeneral = 0;
        for (ProformaItem item : proformaList) {
            totalGeneral += item.getTotal();
        }
        if (tvTotal != null) {
            tvTotal.setText(String.format("Total: S/. %.2f", totalGeneral));
        }
    }
}