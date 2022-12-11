const SERVER_HOST = "http://localhost:2022"
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
                location.assign('/employee.html')
            }
        }).catch(e => { console.log(e) })
    })
} else if (window.location.href.search('employee') > -1) {
    const tableBody = $('section.employee table tbody')
    $.get(SERVER_HOST + '/employee')
        .then(data => {
            const sourceData = JSON.parse(data)
            console.log(sourceData)
            sourceData.forEach(employee => {
                let baseRow = $("<tr></tr>")
                baseRow.append($("<td editable></td>").text(employee.id_employee))
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
}

// $('section.employees table tbody')