/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasCream = whippedCreamCheckbox.isChecked();

        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        EditText nameOfCustomer = (EditText) findViewById(R.id.name_of_customer);
        String text = nameOfCustomer.getText().toString();

//        Log.i("MainActivity", "Так шо, добавляем кремка? " + hasCream);
//        Log.i("mainactivity", "А как нащщот шоколадика? " + hasChocolate);

        int price = calculatePrice(hasCream, hasChocolate);

        displayMessage(createOrderSummary(text, price, hasCream, hasChocolate));

//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo: 48.026, 38.77"));
//        if (intent.resolveActivity(getPackageManager()) != null){
//            startActivity(intent);
//        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
//        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"ssl75@mail.ru"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "заказ для: " + text);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(text, price, hasCream, hasChocolate));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {

        int basePrice = 5;

        if(addWhippedCream) {
            basePrice = basePrice + 1;
        }
        if (addChocolate){
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public void increment(View view) {
        if (quantity == 100) {

            Toast.makeText(this, "До хера кофе заказал!", Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1){

            Toast.makeText(this, "Меньше уже не выйдет...", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     *
     * @param price
     * @param addWhippedCream
     * @return
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocalate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
}