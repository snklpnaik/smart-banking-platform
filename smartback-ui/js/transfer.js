async function transferMoney() {

    const fromAccount = document.getElementById("fromAccount").value.trim();
    const toAccount = document.getElementById("toAccount").value.trim();
    const amount = document.getElementById("amount").value.trim();

    const success = document.getElementById("success");
    const error = document.getElementById("error");

    success.innerHTML = "";
    error.innerHTML = "";

    if (fromAccount === "" || toAccount === "" || amount === "") {
        error.innerHTML = "Please fill all fields.";
        return;
    }

    if (fromAccount === toAccount) {
        error.innerHTML = "Sender and Receiver account cannot be the same.";
        return;
    }

    if (parseFloat(amount) <= 0) {
        error.innerHTML = "Amount should be greater than zero.";
        return;
    }

    try {

        // Change endpoint according to your Transaction Service
        const response = await post("/transaction/transfer", {
            fromAccount: fromAccount,
            toAccount: toAccount,
            amount: parseFloat(amount)
        });

        if (response.ok) {

            success.innerHTML = "Amount transferred successfully.";

            document.getElementById("amount").value = "";

        } else {

            const message = await response.text();
            error.innerHTML = message || "Transfer failed.";

        }

    } catch (e) {

        console.error(e);
        error.innerHTML = "Unable to connect to server.";

    }

}