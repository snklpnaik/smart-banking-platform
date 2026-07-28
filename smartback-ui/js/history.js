async function loadTransactions() {

    checkAuthentication();

    const table = document.getElementById("transactionTable");

    table.innerHTML = "";

    try {

        // Change endpoint according to your Transaction Service
        const response = await get("/transaction/history");

        if (!response.ok) {

            table.innerHTML =
                "<tr><td colspan='5'>Unable to load transactions.</td></tr>";

            return;

        }

        const transactions = await response.json();

        if (transactions.length === 0) {

            table.innerHTML =
                "<tr><td colspan='5'>No transactions found.</td></tr>";

            return;

        }

        transactions.forEach(transaction => {

            table.innerHTML += `
                <tr>
                    <td>${transaction.transactionId}</td>
                    <td>${transaction.transactionType}</td>
                    <td>₹ ${transaction.amount}</td>
                    <td>${transaction.transactionDate}</td>
                    <td>${transaction.status}</td>
                </tr>
            `;

        });

    } catch (e) {

        console.error(e);

        table.innerHTML =
            "<tr><td colspan='5'>Server unavailable.</td></tr>";

    }

}