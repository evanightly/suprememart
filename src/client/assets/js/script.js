const SERVER_HOST = "http://localhost:2022"

const isLogged = () => {
    if (!sessionStorage.getItem('logged')) {
        location.assign('/login.html')
    }
}

const loggedEmployeData = JSON.parse(JSON.parse(sessionStorage.getItem('logged')));

const restrict = (employee) => {
    if (employee === 'cashier') {
        if (!loggedEmployeData.roleTitle) {
            location.assign('/transaction.html')
        }
    } else if (employee === 'manager') {
        if (!loggedEmployeData.transaction_handled) {
            location.assign('/employee.html')
        }
    }
}

const deleteCashier = (id_employee) => {
    $.post(SERVER_HOST + '/employee/' + id_employee).then(d => { location.reload() }).catch(err => console.log(err))
}

$('a#logout').on('click', () => {
    sessionStorage.clear()
})

if (window.location.href.search('login') > -1) {
    $('section.login form').on('submit', e => {
        e.preventDefault()
        console.dir(e)
        console.log(SERVER_HOST + "/login")
        const { username, password } = e.target.elements
        $.post(SERVER_HOST + "/login", {
            username: username.value, password: password.value
        }).then((data) => {
            if (data === "null") alert('Invalid Credential')
            else {
                sessionStorage.setItem('logged', JSON.stringify(data))
                data = JSON.parse(data)
                if (data.roleTitle) {
                    location.assign('/employee.html')
                } else {
                    location.assign('/transaction.html')
                }
            }
        }).catch(e => { console.log(e) })
    })
} else if (window.location.href.search('employee') > -1) {
    restrict('cashier')
    const tableBody = $('section.employee table tbody')
    $.get(SERVER_HOST + '/employee')
        .then(data => {
            const sourceData = JSON.parse(data)
            console.log(sourceData)
            sourceData.forEach(employee => {
                let baseRow = $("<tr></tr>")
                baseRow.append($("<td></td>").text(employee.id_employee))
                baseRow.append($("<td></td>").text(employee.name))
                baseRow.append($("<td></td>").text(employee.username))
                baseRow.append($("<td></td>").text(employee.password))
                baseRow.append($("<td></td>").text(employee.age))
                baseRow.append($("<td></td>").text(employee.salary))
                baseRow.append($("<td></td>").text(employee.years_experienced))
                baseRow.append($("<td></td>").text(employee.transaction_handled))
                baseRow.append($("<td></td>").append($(`<button class="btn btn-danger" onClick="deleteCashier(${employee.id_employee})"></button>`).append(`<i class="bi bi-person-dash-fill"></i>`)))
                tableBody.append(baseRow)
            })
        })
        .catch(e => console.log(e))

    $('.modal form').on('submit', e => {
        e.preventDefault()
        let id_employee = 1;
        const name = $('.modal-body .form #name').val()
        const username = $('.modal-body .form #username').val()
        const password = $('.modal-body .form #password').val()
        const age = $('.modal-body .form #age').val()
        const salary = $('.modal-body .form #salary').val()
        const years_experienced = $('.modal-body .form #years_experienced').val()
        console.log(id_employee, name, username, password, age, salary, years_experienced)
        $.get(SERVER_HOST + '/employee/last').then(data => {
            id_employee += parseInt(data);
            $.post(SERVER_HOST + '/employee', {
                id_employee, name, username, password, age, salary, years_experienced
            }).then(() => {
                console.log('Employee Registered')
                location.reload()
            }).catch(e => console.log(e))
        }).catch(err => console.log(err))
        // const id_employee = $('.modal-body .form #')
        // $.post(SERVER_HOST + '/employee', {})
    })
} else if (window.location.href.search('transaction_add') > -1) {
    restrict('manager')

    const tableBody = $('section.transaction_add table tbody')
    $.get(SERVER_HOST + '/item')
        .then(data => {
            const sourceData = JSON.parse(data)
            // console.log(sourceData)
            sourceData.forEach(item => {
                let baseRow = $("<tr></tr>")
                baseRow.append($("<td></td>").text(item.id_item))
                baseRow.append($("<td></td>").text(item.title))
                baseRow.append($("<td></td>").text(item.category))
                baseRow.append($("<td></td>").text(item.price))
                baseRow.append($("<td></td>").text(item.in_stock))
                baseRow.append($("<td></td>").text(item.in_stock > 0 ? "Available" : "Empty"))
                baseRow.append($("<td></td>").append($(`<input class='form-control' type='number'>`)))
                tableBody.append(baseRow)
            })
        })

    $('.transaction_add form').on('submit', (e) => {
        e.preventDefault();
        let id_transaction = 1, total = 0, id_employee = 1;
        const customer_name = $('input[name="customer_name"]').val()
        let transaction_details = []

        $.get(SERVER_HOST + '/transaction/last').then(data => {
            id_transaction += parseInt(data);
            const inputs = $('input[type="number"]')
            for (const i of inputs) {
                if ($(i).val() > 0) {
                    const id_item = parseInt($(i).parent().parent()[0].children[0].innerHTML)
                    const item_price = parseInt($(i).parent().parent()[0].children[3].innerHTML)
                    const item_quantity = parseInt($(i).val())
                    const item_subtotal = item_quantity * item_price
                    total += item_subtotal
                    transaction_details.push({ id_transaction, customer_name, id_item, item_quantity, item_price, item_subtotal })
                }
            }

            $.post(SERVER_HOST + '/transaction', { id_transaction, id_employee, customer_name, total }).then(e => {
                for (const detail of transaction_details) {
                    // console.log({ id_transaction, id_item: detail.id_item, quantity: detail.item_quantity, subtotal: detail.item_subtotal })
                    $.post(SERVER_HOST + '/detail_transaction', { id_transaction, id_item: detail.id_item, quantity: detail.item_quantity, subtotal: detail.item_subtotal })
                }
            })
        }).catch(err => console.log(err))
    })
} else if (window.location.href.search('transaction') > -1) {
    restrict('manager')

    const tableBody = $('section.transaction table tbody')
    $.get(SERVER_HOST + '/transaction')
        .then(data => {
            const sourceData = JSON.parse(data)
            console.log(sourceData)
            sourceData.forEach(transaction => {
                let baseRow = $("<tr></tr>")
                baseRow.append($("<td></td>").text(transaction.id_transaction))
                baseRow.append($("<td></td>").text(transaction.employee_name))
                baseRow.append($("<td></td>").text(transaction.customer_name))
                baseRow.append($("<td></td>").text(transaction.transaction_date))
                baseRow.append($("<td></td>").text(transaction.total))
                baseRow.append($("<td></td>").append($(`<button class="btn btn-danger" onClick="deleteTransaction(${transaction.id_transaction})"></button>`).append(`<i class="bi bi-person-dash-fill"></i>`)))
                tableBody.append(baseRow)
            })
        })
} 