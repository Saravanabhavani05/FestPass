document.addEventListener("DOMContentLoaded", function () {

    loadDashboardData();

});


async function loadDashboardData() {

    try {

        // Get events
        const eventsResponse = await fetch("/api/events");
        const events = await eventsResponse.json();

        // Get attendees
        const attendeesResponse = await fetch("/api/attendees");
        const attendees = await attendeesResponse.json();

        // Get tickets
        const ticketsResponse = await fetch("/api/tickets");
        const tickets = await ticketsResponse.json();


        // -----------------------------
        // DASHBOARD COUNTS
        // -----------------------------

        document.getElementById("eventCount").textContent =
            events.length;

        document.getElementById("attendeeCount").textContent =
            attendees.length;

        document.getElementById("ticketCount").textContent =
            tickets.length;


        // Count checked-in tickets
        const checkedInTickets = tickets.filter(
            ticket => ticket.status === "USED"
        );

        document.getElementById("checkinCount").textContent =
            checkedInTickets.length;


        // -----------------------------
        // UPCOMING EVENTS
        // -----------------------------

        displayUpcomingEvents(events);


    } catch (error) {

        console.error("Dashboard data loading failed:", error);

    }

}


/* =========================================
   DISPLAY UPCOMING EVENTS
   ========================================= */

function displayUpcomingEvents(events) {

    const eventContainer = document.querySelector(".dashboard-card .card-body");

    if (!eventContainer) {
        return;
    }


    if (events.length === 0) {

        eventContainer.innerHTML = `
            <div style="
                padding: 30px;
                text-align: center;
                color: #687287;
                font-size: 12px;
            ">
                No events available.
            </div>
        `;

        return;
    }


    eventContainer.innerHTML = "";


    events.forEach(function (event) {

        let eventDate = new Date(event.eventDate);

        let day = eventDate.getDate();

        let month = eventDate.toLocaleString("en-US", {
            month: "short"
        }).toUpperCase();


        const eventItem = document.createElement("div");

        eventItem.className = "event-item";


        eventItem.innerHTML = `

            <div class="event-info">

                <div class="event-date-box">

                    <strong>${day}</strong>

                    <span>${month}</span>

                </div>


                <div>

                    <div class="event-name">
                        ${event.eventName}
                    </div>

                    <div class="event-location">
                        ${event.venue}
                    </div>

                </div>

            </div>


            <div class="event-status">
                ${event.active ? "Active" : "Inactive"}
            </div>

        `;


        eventContainer.appendChild(eventItem);

    });

}


/* =========================================
   LOGIN
   ========================================= */

const loginForm = document.getElementById("loginForm");


if (loginForm) {

    loginForm.addEventListener("submit", function (event) {

        event.preventDefault();


        const email =
            document.getElementById("email").value.trim();

        const password =
            document.getElementById("password").value.trim();


        if (email !== "" && password !== "") {

            window.location.href = "dashboard.html";

        } else {

            alert("Please enter your email and password.");

        }

    });

}