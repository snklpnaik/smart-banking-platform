async function depositMoney() {

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

    if (Number(amount) <= 0) {
        error.innerHTML = "Amount must be greater than zero.";
        return;
    }

    try {

        // Update endpoint according to your backend
        const response = await post("/account/deposit", {
            accountNumber: accountNumber,
            amount: Number(amount)
        });

        if (response.ok) {

            success.innerHTML = "Amount deposited successfully.";

            document.getElementById("amount").value = "";

        } else {

            const message = await response.text();
            error.innerHTML = message || "Deposit failed.";

        }

    } catch (e) {

        console.error(e);
        error.innerHTML = "Unable to connect to server.";

    }

}