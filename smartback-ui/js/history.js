async function loadTransactions() {

    checkAuthentication();

    const accountNumber =
        sessionStorage.getItem("accountNumber");

    const table =
        document.getElementById("transactionTable");

    table.innerHTML = "";

    try {

        const response =
            await get(API.TRANSACTIONS + "/" + accountNumber);

        if (!response.ok) {

            table.innerHTML = `
                <tr>
                    <td colspan="6">
                        Unable to load transactions.
                    </td>
                </tr>
            `;

            return;

        }

        const transactions = await response.json();

        if (transactions.length === 0) {

            table.innerHTML = `
                <tr>
                    <td colspan="6">
                        No Transactions Found
                    </td>
                </tr>
            `;

            return;

        }

        transactions.forEach(transaction => {

            table.innerHTML += `

                <tr>

                    <td>${transaction.fromAccountNumber}</td>

                    <td>${transaction.toAccountNumber}</td>

                    <td>${transaction.transactionType}</td>

                    <td>₹ ${transaction.amount}</td>

                    <td>${transaction.transactionStatus}</td>

                    <td>${transaction.createdAt}</td>

                </tr>

            `;

        });

    }
    catch (e) {

        console.error(e);

        table.innerHTML = `
            <tr>
                <td colspan="6">
                    Server unavailable.
                </td>
            </tr>
        `;

    }

}