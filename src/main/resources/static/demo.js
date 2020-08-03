function initialize() {

    getStudents("/api/students");
    renderModal("createStudent");
    renderModal("updateStudent");
}

function getStudents(url) {

    var xhttpList = new XMLHttpRequest();

    xhttpList.onreadystatechange = function () {

        if (this.readyState == 4 && this.status == 200) {
            renderStudent(this.responseText);
        }
    };
    xhttpList.open("GET", url, true);
    xhttpList.send();
    console.log("Student list received");
}

function renderStudent(data) {

    var json = JSON.parse(data);

    var cardHtml = '  <h2>Our Students</h2>'
        + '<div class="card bg-primary"  style="width:400px">'
        + '<img class="card-img-top" src= https://thumbs.dreamstime.com/z/happy-university-college-student-thumbs-up-15010463.jpg alt="Card image" style="width:100%">'
        + '<div class="card-body">'
        + '<h4 class="card-title">' + json[0].firstName + ' ' + json[0].lastName + '</h4>'
        + '<p class="card-text">Some example text some example text. John Doe is an architect and engineer</p>'
        + '<a href="#" class="btn btn-warning">DELETE</a>'
        + '</div>'
        + '</div>';

    document.getElementById("students").insertAdjacentHTML('beforeend', cardHtml);

}

function renderModal(modalPurpose) {

    var modalHtml = '<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#'+ modalPurpose +'">Open modal</button>'
    + ' <div class="modal fade" id="' + modalPurpose + '"> '
    + ' <div class="modal-dialog modal-xl"> '
    + ' <div class="modal-content"> '

    + '<div class="modal-header">'
    + '<h4 class="modal-title">Modal Heading</h4>'
    + '<button type="button" class="close" data-dismiss="modal">&times;</button>'
    + '</div>'

    + '<div class="modal-body">'
    + 'Modal body..'
    + '</div>'

    + '<div class="modal-footer">'
    + '<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>'
    + '</div>'

    + '</div>'
    + '</div>'
    + '</div>'
    + '</div>';

    document.getElementById("modals").insertAdjacentHTML('beforeend', modalHtml);
}

function deleteStudent(id) {
    var link = "/api/delete/student/" + id;

    var ok = confirm("Are you sure you want to delete this student?\nPress 'ok' to continue, or 'cancel' to abort");

    if (ok == true) {

        var xhttp = new XMLHttpRequest();
        xhttp.open("DELETE", link, true);

        xhttp.onreadystatechange = function () {
            if(this.readyState == 4 && this.status == 200){
                var removeCard = document.getElementById(id);

                removeCard.parentNode.removeChild(removeCard);
                console.log("Student deleted");
            }
        };
        xhttp.send(null);

    }
}
