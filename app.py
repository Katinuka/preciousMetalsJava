from flask import Flask, Response
from collections import OrderedDict
import json
import random
import threading
import time

app = Flask(__name__)

# Dictionary to store metal prices
metal_prices = OrderedDict([
    ('Gold', 2725.0),
    ('Silver', 33.0),
    ('Platinum', 1350.0),
    ('Palladium', 1500.0)
])


# Function to generate the next price based on the previous price
def generate_next_price(current_price, min_value=1000, max_value=10000, step=20):
    # Generate a random delta within the allowed step range
    delta = random.uniform(-step, step)

    # Check if applying the delta would exceed the boundaries
    if current_price + delta > max_value:
        delta = -abs(delta)  # Make delta negative to reverse the change
    elif current_price + delta < min_value:
        delta = abs(delta)  # Make delta positive to reverse the change

    # Apply the delta to get the new price
    new_price = current_price + delta

    # Ensure the price stays within bounds
    return round(new_price, 2)


# Function to update metal prices every second
def update_prices():
    while True:
        for metal in metal_prices:
            # Update each metal's price using the restricted change function
            metal_prices[metal] = generate_next_price(metal_prices[metal])
        time.sleep(3)  # Wait for 1 second before updating again


# Route to get the current metal prices
@app.route('/api/metals/prices', methods=['GET'])
def get_metal_prices():
    # Use json.dumps to convert OrderedDict to JSON string while preserving order
    return Response(json.dumps(metal_prices), mimetype='application/json')


# Start the background thread to update prices
def start_price_updater():
    price_thread = threading.Thread(target=update_prices)
    price_thread.daemon = True  # Daemon thread will exit when the main program exits
    price_thread.start()


if __name__ == '__main__':
    # Start the background price updater thread
    start_price_updater()
    # Run the Flask app
    app.run(debug=True, host='127.0.0.1', port=5000)
