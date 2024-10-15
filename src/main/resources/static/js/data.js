export async function getUserData() {
    try {
        const response = await fetch('/api/users/current');
        return await response.json();
    } catch (error) {
        console.error('Error fetching user data:', error);
        return null;
    }
}

export async function getMetalPrices() {
    try {
        const response = await fetch('/api/metals');
        return await response.json();
    } catch (error) {
        console.error('Error fetching metal prices:', error);
        return null;
    }
}

export async function sendTransaction(action, metal, ounces, pricePerOunce) {
    const endpoint = action === "buy" ? '/buy-metal' : '/sell-metal';
    try {
        const response = await fetch(endpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                metal,
                ounces,
                pricePerOunce,
            }),
        });
        return await response.json();
    } catch (error) {
        console.error('Error:', error);
        return 'error';
    }
}