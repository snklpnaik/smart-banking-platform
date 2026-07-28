async function withdrawMoney() {

    const accountNumber = document.getElementById("accountNumber").value.trim();
    const amount = document.getElementById("amount").value.trim();

    const success = document.getElementById("success");
    const error = document.getElementById("error");

    success.innerHTML = "";
    error.innerHTML = "";

    if (accountNumber === "" || amount === "") {
        error.innerHTML = "Please fill all fields.";
        return;
    }

    if (parseFloat(amount) <= 0) {
        error.innerHTML = "Amount should be greater than zero.";
        return;
    }

    try {

        // Change endpoint according to your Account Service
        const response = await post("/account/withdraw", {
            accountNumber: accountNumber,
            amount: parseFloat(amount)
        });

        if (response.ok) {

            success.innerHTML = "Amount withdrawn successfully.";

            document.getElementById("amount").value = "";

        } else {

            const message = await response.text();
            error.innerHTML = message || "Withdrawal failed.";

        }

    } catch (e) {

        console.error(e);
        error.innerHTML = "Unable to connect to server.";

    }

}