package com.oscararbo.calculadora;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView pantalla;
    private boolean aux = false;
    private String op = "";
    private String ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        pantalla = findViewById(R.id.textViewPantalla);
        pantalla.setMovementMethod(new ScrollingMovementMethod());
        pantalla.setText("0");

        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonResult = findViewById(R.id.buttonResult);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonDot = findViewById(R.id.buttonDot);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonReset = findViewById(R.id.buttonReset);
        Button buttonPow = findViewById(R.id.buttonPow);
        Button buttonPercent = findViewById(R.id.buttonPercent);
        Button buttonAns = findViewById(R.id.buttonAns);
        Button buttonSQRT = findViewById(R.id.buttonSQRT);
        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);

        buttonDot.setOnClickListener(view -> insertar("."));
        buttonPlus.setOnClickListener(view -> insertarOperador("+"));
        buttonMinus.setOnClickListener(view -> insertarOperador("-"));
        buttonMultiply.setOnClickListener(view -> insertarOperador("*"));
        buttonDivide.setOnClickListener(view -> insertarOperador("/"));

        buttonAns.setOnClickListener(view -> insertar("Ans"));
        buttonPercent.setOnClickListener(view -> insertarOperador("%"));
        buttonPow.setOnClickListener(view -> insertarOperador("^"));
        buttonSQRT.setOnClickListener(view -> insertarOperador("√"));

        buttonResult.setOnClickListener(view -> resultado());
        buttonReset.setOnClickListener(view -> resetPantalla());
        button0.setOnClickListener(view -> insertar("0"));
        button1.setOnClickListener(view -> insertar("1"));
        button2.setOnClickListener(view -> insertar("2"));
        button3.setOnClickListener(view -> insertar("3"));
        button4.setOnClickListener(view -> insertar("4"));
        button5.setOnClickListener(view -> insertar("5"));
        button6.setOnClickListener(view -> insertar("6"));
        button7.setOnClickListener(view -> insertar("7"));
        button8.setOnClickListener(view -> insertar("8"));
        button9.setOnClickListener(view -> insertar("9"));
    }

    private void insertar(String valor) {
        String textoPantalla = pantalla.getText().toString();
        switch (valor) {
            case "C":
                resetPantalla();
                break;
            case ".":
                if (op.contains(textoPantalla.substring(textoPantalla.length() - 1))) {
                    pantalla.append("0.");
                    aux = true;
                } else if (!aux) {
                    pantalla.append(".");
                    aux = true;
                }
                break;
            case "Ans":
                if (textoPantalla.equals("0")){
                    pantalla.setText(ans);
                }else {
                    pantalla.append(ans);
                }
                break;
            default:
                if (textoPantalla.equals("0")) {
                    pantalla.setText(valor);
                } else {
                    pantalla.append(valor);
                }
                break;
        }
    }

    private void insertarOperador(String operador) {
        String textoPantalla = pantalla.getText().toString();
        if (!textoPantalla.contains("+") && !textoPantalla.contains("-") && !textoPantalla.contains("*") && !textoPantalla.contains("/") && !textoPantalla.contains("^") && !textoPantalla.contains("%")) {
            pantalla.append(operador);
            op = operador;
            aux = false;
        }else {
            resultado();
            pantalla.append(operador);
            op = operador;
            aux = false;
        }
    }
    private void resultado() {
        String textoPantalla = pantalla.getText().toString();
        try {
            double result = calcular(textoPantalla);
            if (result == (int) result) {
                pantalla.setText(String.valueOf((int) result));
            } else {
                pantalla.setText(String.valueOf(result));
            }


            aux = true;
            ans = pantalla.getText().toString();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    private void resetPantalla() {
        pantalla.setText("0");
        aux = false;
        op = "";
    }
    public double calcular(String operacion) {
        String operador;
        switch (op) {
            case "+":
                operador="\\+";
                break;
            case "*":
                operador="\\*";
                break;
            case "^":
                operador="\\^";
                break;
            default:
                operador=op;
                break;
        }

        String[] valores = operacion.split(operador);
        double num1 = Double.parseDouble(valores[0]);
        double num2 = Double.parseDouble(valores[1]);
        double result = 0;
        switch (operador) {
            case "\\+":
                result=num1+num2;
                break;
            case "-":
                result=num1- num2;
                break;
            case "\\*":
                result=num1* num2;
                break;
            case "/":
                if (num2 !=0) {
                    result=num1/num2;
                } else {
                    throw new ArithmeticException("No se puede dividir entre 0");
                }
                break;
            case "\\^":
                result =Math.pow(num1,num2);
                break;
            case "%":
                result =(num1 /100)*num2;
                break;
            case "√":
                if (num2 !=0){
                    result = Math.pow(num1, 1/num2);
                }else {
                    throw new ArithmeticException("No se puede realizar esta raiz");
                }
                break;
        }
        return result;
    }
}
