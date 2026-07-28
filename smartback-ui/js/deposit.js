function loadDepositPage() {

    checkAuthentication();

    document.getElementById("accountNumber").innerHTML =
        sessionStorage.getItem("accountNumber");

    document.getElementById("accountType").innerHTML =
        sessionStorage.getItem("accountType");

    document.getElementById("balance").innerHTML =
        "₹ " + sessionStorage.getItem("balance");

}

async function depositMoney() {

    const amount = document.getElementById("amount").value.trim();

    const success = document.getElementById("success");
    const error = document.getElementById("error");

    success.innerHTML = "";
    error.innerHTML = "";

    if (amount === "") {

        error.innerHTML = "Please enter amount.";
        return;

    }

    if (parseFloat(amount) <= 0) {

        error.innerHTML = "Amount should be greater than zero.";
        return;

    }

    try {

        const request = {

            accountNumber: sessionStorage.getItem("accountNumber"),
            amount: parseFloat(amount)

        };

        const response = await post(API.DEPOSIT, request);

        if (!response.ok) {

            const message = await response.text();

            error.innerHTML = message || "Deposit failed.";

            return;

        }

        success.innerHTML = "Amount deposited successfully.";

        // Update balance in dashboard cache

        const balance =
            parseFloat(sessionStorage.getItem("balance"));

        const newBalance = balance + parseFloat(amount);

        sessionStorage.setItem("balance", newBalance);

        document.getElementById("balance").innerHTML =
            "₹ " + newBalance.toFixed(2);

        document.getElementById("amount").value = "";

    }
    catch (e) {

        console.error(e);

        error.innerHTML = "Unable to connect to server.";

    }

}