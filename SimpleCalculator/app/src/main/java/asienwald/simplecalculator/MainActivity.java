package asienwald.simplecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    TextView txtNumber = (TextView) findViewById(R.id.txtNumber);

    private String[] numbers = new String[2];
    private String op; //operator

    public void ButtonClick(View v){
        Button button = (Button) v;
        if("0123456789.".contains(button.getText())){
            AddNumberorDecimalPoint(button.getText());
        }else if("÷×-+".contains(button.getText())){
            AddOperator(button.getText());
        }else if("=" == button.getText()){
            Calculate();
        }else{
            Erase();
        }
    }

    public void AddNumberorDecimalPoint(String value){
        int index = op == null ? 0 : 1;
        if(value == "." && numbers[index].contains(".")){
            return;
        }
        numbers[index] += value;

        UpdateCalculatorText();
    }

    public void AddOperator(String value){
        if(numbers[1] != null){
            Calculate(value);
        }
    }

    public void Calculate(String newOp = null){

    }

    public void Erase(){

    }

    public void UpdateCalculatorText(){
        String text = numbers[0].toString() + " " + op + " " + numbers[1].toString();
        txtNumber.setText(text);
    }

}
