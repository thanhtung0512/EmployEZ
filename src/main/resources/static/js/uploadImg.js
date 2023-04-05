// const name = document.querySelector('.name h1').textContent;
// const specialize = document.querySelector('.name .specialize').textContent;
// const phone = document.querySelector('.name .contact li:nth-of-type(1) span:last-of-type').textContent;
// const email = document.querySelector('.name .contact li:nth-of-type(2) span:last-of-type').textContent;
// const website = document.querySelector('.name .contact li:nth-of-type(3) span:last-of-type').textContent;
// const avatarInput = document.querySelector('#avatar-input');
// const avatarImg = document.querySelector('#avatar-img');

// avatarInput.addEventListener('change', () => {
//     const file = avatarInput.files[0];
//     const reader = new FileReader();

//     reader.addEventListener('load', () => {
//         avatarImg.setAttribute('src', reader.result);
//     });

//     if (file) {
//         reader.readAsDataURL(file);
//     }
// });
// const avatarBtn = document.querySelector('#avatar-btn');

// avatarBtn.addEventListener('click', () => {
//     avatarInput.click();
// });
const avatarInput = document.querySelector('#avatar-input');
const avatarImg = document.querySelector('#avatar-img');
const avatarBtn = document.querySelector('#avatar-btn');

avatarInput.addEventListener('change', () => {
    const file = avatarInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', () => {
        avatarImg.setAttribute('src', reader.result);
    });

    if (file) {
        reader.readAsDataURL(file);
        uploadAvatar(file);
    }
});

function uploadAvatar(file) {
    const formData = new FormData();
    formData.append('avatar', file);

    fetch('/upload-avatar', {
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error(error));
}

avatarBtn.addEventListener('click', () => {
    avatarInput.click();
});