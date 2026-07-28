function loadTransferPage() {

    checkAuthentication();

    document.getElementById("fromAccount").innerHTML =
        sessionStorage.getItem("accountNumber");

    document.getElementById("accountType").innerHTML =
        sessionStorage.getItem("accountType");

    document.getElementById("balance").innerHTML =
        "₹ " + sessionStorage.getItem("balance");

}

async function transferMoney() {

    const fromAccount =
        sessionStorage.getItem("accountNumber");

    const toAccount =
        document.getElementById("toAccount").value.trim();

    const amount =
        document.getElementById("amount").value.trim();

    const success =
        document.getElementById("success");

    const error =
        document.getElementById("error");

    success.innerHTML = "";
    error.innerHTML = "";

    if (toAccount === "" || amount === "") {

        error.innerHTML = "Please fill all fields.";
        return;

    }

    if (fromAccount === toAccount) {

        error.innerHTML =
            "Cannot transfer to the same account.";

        return;

    }

    if (parseFloat(amount) <= 0) {

        error.innerHTML =
            "Amount should be greater than zero.";

        return;

    }

    const balance =
        parseFloat(sessionStorage.getItem("balance"));

    if (parseFloat(amount) > balance) {

        error.innerHTML =
            "Insufficient balance.";

        return;

    }

    try {

        const request = {

            fromAccountNumber: fromAccount,
            toAccountNumber: toAccount,
            amount: parseFloat(amount)

        };

        const response =
            await post(API.TRANSFER, request);

        if (!response.ok) {

            const message = await response.text();

            error.innerHTML =
                message || "Transfer failed.";

            return;

        }

        const newBalance =
            balance - parseFloat(amount);

        sessionStorage.setItem(
            "balance",
            newBalance
        );

        document.getElementById("balance").innerHTML =
            "₹ " + newBalance.toFixed(2);

        document.getElementById("amount").value = "";
        document.getElementById("toAccount").value = "";

        success.innerHTML =
            "Amount transferred successfully.";

    }
    catch (e) {

        console.error(e);

        error.innerHTML =
            "Unable to connect to server.";

    }

}