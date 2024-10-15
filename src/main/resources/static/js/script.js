import {error, success} from "./alerts.js";
import {getMetalPrices, getUserData, sendTransaction} from "./data.js";

let metalPrices = {};
let user

async function updateMetalPrices() {
    metalPrices = await getMetalPrices();  // Wait for the metal prices to be fetched

    // Clear the price table body
    const priceTableBody = document.getElementById('price-table-body');
    priceTableBody.innerHTML = ''; // Clear existing rows

    // Populate the price table with updated data
    for (const [metal, price] of Object.entries(metalPrices)) {
        const row = document.createElement('tr');

        const metalCell = document.createElement('td');
        metalCell.textContent = metal;
        row.appendChild(metalCell);

        const priceCell = document.createElement('td');
        priceCell.textContent = price.toFixed(2); // Format price to 2 decimal places
        row.appendChild(priceCell);

        priceTableBody.appendChild(row);
    }
}

function updateTotalPrice() {
    const metal = document.getElementById("metal").value;
    const ounces = document.getElementById("ounces").value;
    const pricePerOunce = metalPrices[metal];

    if (pricePerOunce && ounces) {
        const totalPrice = pricePerOunce * ounces;
        document.getElementById("total-price").innerText = totalPrice.toFixed(2);
    }
}

function updateMaxOunces() {
    const metal = document.getElementById('metal').value;
    const transactionType = document.getElementById('transaction-type').value;
    let maxOunces = 0;

    if (transactionType === 'buy') {
        const metalPrice = metalPrices[metal];

        if (metalPrice && user.balance['USD']) {
            maxOunces = user.balance['USD'] / metalPrice;
        }
    } else if (transactionType === 'sell') {
        maxOunces = user.balance[metal] || 0;
    }

    document.getElementById('ounces').setAttribute('max', maxOunces.toFixed(2));
}

async function updateUserBalance() {
    user = await getUserData()
    const userBalanceElement = document.querySelector('.user-info ul');

    // Clear the existing balance
    userBalanceElement.innerHTML = '';

    // Loop through the updated balance and update the UI
    for (const [currency, balance] of Object.entries(user.balance)) {
        const li = document.createElement('li');

        // Create span for currency key
        const currencySpan = document.createElement('span');
        currencySpan.textContent = currency;

        // Create separator text
        const separator = document.createTextNode(': ');

        // Create span for balance value
        const balanceSpan = document.createElement('span');
        balanceSpan.textContent = balance.toFixed(2);

        // Append the spans and text to the li
        li.appendChild(currencySpan);
        li.appendChild(separator);
        li.appendChild(balanceSpan);

        // Append the updated li to the user balance list
        userBalanceElement.appendChild(li);
    }
}

async function performTransaction() {
    const action = document.getElementById("transaction-type").value;
    const metal = document.getElementById("metal").value;
    const ounces = document.getElementById("ounces").value;
    const pricePerOunce = metalPrices[metal];
    const totalPrice = pricePerOunce * ounces;

    if (!metal || !ounces || ounces <= 0) {
        error('Please enter a valid amount of ounces')
        return;
    }

    let userBalance = user.balance['USD']
    if (action === "buy" && totalPrice > userBalance) {
        const required = totalPrice.toFixed(2)
        const actual = userBalance.toFixed(2)
        error(`Insufficient balance (${actual} < ${required} USD)`)
        return;
    }

    let userOunces = user.balance[metal]
    if (action === "sell" && ounces > userOunces) {
        error(`Insufficient balance (${userOunces} < ${ounces})`)
        return;
    }

    const data = await sendTransaction(action, metal, ounces, pricePerOunce)
    if (data.status === 'success') {
        const act = action === 'buy' ? 'Purchase' : 'Sale'
        success(`${act} successful!`)
        await updateUserBalance()
    } else {
        error(data.message)
        console.error(data.message)
    }
}

async function update() {
    await updateMetalPrices();
    updateTotalPrice()
    updateMaxOunces()
}

// Event listeners to update total price when input changes
document.getElementById("metal").addEventListener("change", updateTotalPrice);
document.getElementById("metal").addEventListener("change", updateMaxOunces);
document.getElementById("ounces").addEventListener("input", updateTotalPrice);

setInterval(update, 100);

window.onload = async () => {
    await updateUserBalance();
    await update();
};
// Attach performTransaction to the global window object
window.performTransaction = performTransaction;