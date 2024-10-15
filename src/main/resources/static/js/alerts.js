export function error(text) {
    Swal.fire({
        icon: 'error',
        title: 'Error',
        text: text
    });
}

export function success(text) {
    Swal.fire({
        icon: 'success',
        title: 'Success',
        text: text
    });
}