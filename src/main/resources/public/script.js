const URL = 'http://localhost:8081';
let entries = [];

const   dateAndTimeToDate = (dateString, timeString) => {
    return new Date(`${dateString}T${timeString}`).toISOString();
};

const createEntry = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const entry = {};
    entry['checkIn'] = dateAndTimeToDate(formData.get('checkInDate'), formData.get('checkInTime'));
    entry['checkOut'] = dateAndTimeToDate(formData.get('checkOutDate'), formData.get('checkOutTime'));

    fetch(`${URL}/entries`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(entry)
    }).then((result) => {
        result.json().then((entry) => {
            entries.push(entry);
            renderEntries();
        });
    });
};

const deleteEntry = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    let entryId = parseInt(formData.get('entryId'));
    fetch(`${URL}/entries/${entryId}`, {
        method: 'DELETE'
    }).then( () => {
        indexEntries();
    }).catch((error) => {
        console.error(error);
    })
};

const updateEntry = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const entry = {};
    entry['id'] = formData.get('entryUpdateId');
    entry['checkIn'] = dateAndTimeToDate(formData.get('checkInDate'), formData.get('checkInTime'));
    entry['checkOut'] = dateAndTimeToDate(formData.get('checkOutDate'), formData.get('checkOutTime'));

    fetch(`${URL}/entries`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(entry)
    }).then(() => {
        indexEntries();
    });
};

const indexEntries = () => {
    fetch(`${URL}/entries`, {
        method: 'GET'
    }).then((result) => {
        result.json().then((result) => {
            entries = result;
            renderEntries();
        });
    });
    renderEntries();
};

const createCell = (text) => {
    const cell = document.createElement('td');
    cell.innerText = text;
    return cell;
};

const renderEntries = () => {
    const display = document.querySelector('#entryDisplay');
    display.innerHTML = '';
    entries.forEach((entry) => {
        const row = document.createElement('tr');
        row.appendChild(createCell(entry.id));
        row.appendChild(createCell(new Date(entry.checkIn).toLocaleString()));
        row.appendChild(createCell(new Date(entry.checkOut).toLocaleString()));
        display.appendChild(row);
    });
};

const autoFillValues = (e) => {
    const id = parseInt(e.target.value);
    for (let i = 0; i < entries.length; i++) {
        if (entries[i].id === id) {
            const checkInDate = document.querySelector('#checkInDate');
            checkInDate.value =  entries[i].checkIn.split('T')[0];
            const checkInTime = document.querySelector('#checkInTime');
            checkInTime.value = entries[i].checkIn.split('T')[1];

            const checkOutDate = document.querySelector('#checkOutDate');
            checkOutDate.value = entries[i].checkOut.split('T')[0];
            const checkOutTime = document.querySelector('#checkOutTime');
            checkOutTime.value = entries[i].checkOut.split('T')[1];
        }
    }
};

/*
 * Set Event Listeners
 */

document.addEventListener('DOMContentLoaded', function(){
    const updateEntryForm = document.querySelector('#updateEntryForm');
    updateEntryForm.addEventListener('submit', updateEntry);
    indexEntries();
});

document.addEventListener('DOMContentLoaded', function(){
    const deleteEntryForm = document.querySelector('#deleteEntryForm');
    deleteEntryForm.addEventListener('submit', deleteEntry);
    indexEntries();
});

document.addEventListener('DOMContentLoaded', function(){
    const createEntryForm = document.querySelector('#createEntryForm');
    createEntryForm.addEventListener('submit', createEntry);
    indexEntries();
});

/**
 * If a entry id is selected for the update function, auto fill input fields
 */
document.addEventListener('DOMContentLoaded', function(){
    const entryIdInput = document.querySelector('#entryUpdateId');
    entryIdInput.addEventListener('change', autoFillValues);
});
