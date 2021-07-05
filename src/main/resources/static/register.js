function loadDepartments() {
    $.ajax("/api/departments/findAll").done(function(data) {
        var i;
        for (i = 0; i < data.length; i++) {
            var department = data[i];
            var optionElement = document.createElement('option');
            optionElement.value = department.id;
            optionElement.textContent = department.departmentName;
            $('select').append(optionElement);
        }
    });
}