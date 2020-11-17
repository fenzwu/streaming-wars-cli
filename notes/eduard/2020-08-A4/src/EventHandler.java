import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

public final class EventHandler {
    public void create_demo(
            Boolean verboseMode,
            String short_name,
            String long_name,
            int num_accounts,
            ArrayList<DemographicGroup> demographicGroups
    ) {
        boolean name_exists = demographicGroups.stream().anyMatch(o -> o.short_name.equals(short_name));
        if (!name_exists) {
            DemographicGroup demoGroup = new DemographicGroup(short_name,long_name,num_accounts);
            demographicGroups.add(demoGroup);
            if (verboseMode) { System.out.println("Created demo group named " + short_name); }
        } else {
            if (verboseMode) { System.out.println("Did not create demo -> already exists."); }
        }
    }

    public void create_studio(Boolean verboseMode, String short_name, String long_name, ArrayList<Studio> studios) {
        boolean name_exists = studios.stream().anyMatch(o -> o.short_name.equals(short_name));
        if (!name_exists) {
            Studio studio = new Studio(short_name,long_name);
            studios.add(studio);
            if (verboseMode) { System.out.println("Created studio named " + short_name); }
        } else {
            if (verboseMode) { System.out.println("Studio not created -> already exists."); }
        }
    }

    public void create_event(
            Boolean verboseMode,
            String event_type,
            String name,
            int year_produced,
            int duration,
            String studio_name,
            int license_fee,
            ArrayList<Studio> studios,
            ArrayList<Event> events
    ) {
        // Check for existing studio
        Optional<Studio> studio = studios.stream().filter(o -> o.short_name.equals(studio_name)).findFirst();
        if (studio.isEmpty()) {
            if (verboseMode) { System.out.println("Event not created -> no studio found named " + studio_name); }
            return;
        }

        // Check for existing event
        if (events.stream().anyMatch(o -> o.unique_id().equals(name + year_produced))) {
            if (verboseMode) { System.out.println("Event not created -> already exists."); }
            return;
        }

        switch (event_type) {
            case "movie":
                Movie movie = new Movie(name, year_produced, duration, studio.get(), license_fee);
                if (verboseMode) { System.out.println("Created Movie named " + name); }
                studio.get().movies.add(movie);
                events.add(movie);
                break;

            case "ppv":
                PayPerView pay_per_view = new PayPerView(name, year_produced, duration, studio.get(), license_fee);
                if (verboseMode) { System.out.println("Created PayPerView named " + name); }
                studio.get().payPerViews.add(pay_per_view);
                events.add(pay_per_view);
                break;

            default:
                if (verboseMode) { System.out.println("Event was not created -> type is invalid."); }
                break;
        }
    }

    public void create_stream(
            Boolean verboseMode,
            String short_name,
            String long_name,
            int subscription_price,
            ArrayList<StreamingService> streamingServices
    ) {
        boolean service_exists = streamingServices.stream().anyMatch(o -> o.short_name.equals(short_name));
        if (!service_exists) {
            StreamingService service = new StreamingService(short_name,long_name,subscription_price);
            streamingServices.add(service);
            if (verboseMode) { System.out.println("Created Streaming Service named " + short_name); }
        } else {
            if (verboseMode) { System.out.println("Streaming service was not created - already exists."); }
        }
    }

    public void offer_event(
            boolean verboseMode,
            String offerType,
            String streamingServiceName,
            String eventName,
            int eventYear,
            String[] tokens,
            ArrayList<StreamingService> streamingServices,
            ArrayList<Event> events,
            ArrayList<String> offers
    ) {
        // Check for existing streaming service.
        Optional<StreamingService> existingService = streamingServices
                .stream()
                .filter(o -> o.short_name.equals(streamingServiceName))
                .findFirst();
        if (existingService.isEmpty()) {
            if (verboseMode) { System.out.println("Cannot offer event -> Streaming service does not exist."); }
            return;
        }

        // Make sure the event exists.
        Optional<Event> existingEvent = events
                .stream()
                .filter(o -> o.unique_id().equals(eventName + eventYear))
                .findFirst();
        if (existingEvent.isEmpty()) {
            if (verboseMode) { System.out.println("Cannot offer event -> Event does not exist."); }
            return;
        }

        switch (offerType) {
            case "movie":
                if (!(existingEvent.get() instanceof Movie)) {
                    if (verboseMode) { System.out.println("Incorrect command -> Use offer_ppv for ppv events."); }
                } else {
                    Movie movie = (Movie) existingEvent.get();
                    StreamingService service = existingService.get();
                    if (!service.getMovies().contains(movie)) {
                        service.addMovie(movie);
                        movie.studio.licenseEvent(movie);
                        offers.add(service.short_name + ",movie," + movie.name + "," + movie.year_produced);
                        if (verboseMode) { System.out.println("Added " + movie.name + " to " + service.short_name); }
                    } else {
                        if (verboseMode) { System.out.println("The service " + service.short_name + "is already offering this movie."); }
                    }
                }
                break;

            case "ppv":
                int eventPrice = Integer.parseInt(tokens[4]);
                if (!(existingEvent.get() instanceof PayPerView)) {
                    if (verboseMode) {
                        System.out.println("Incorrect command -> Use offer_movie for movie events.");
                    }
                } else {
                    PayPerView payPerView = (PayPerView) existingEvent.get();
                    StreamingService service = existingService.get();
                    if (!service.getPayPerViews().contains(payPerView)) {
                        payPerView.setPriceForStreamingService(eventPrice, service.short_name);
                        service.addPayPerview(payPerView);
                        payPerView.studio.licenseEvent(payPerView); // current month revenue is increased.
                        offers.add(service.short_name + ",ppv," + payPerView.name + "," + payPerView.year_produced + "," + eventPrice);
                        if (verboseMode) { System.out.println("Added " + payPerView.name + " to " + service.short_name); }
                    } else {
                        if (verboseMode) {
                            System.out.println("The service " + service.short_name + "is already offering this PayPerView.");
                        }
                    }
                }
                break;

            default:
                break;
        }
    }

    public void watch_event(
            boolean verboseMode,
            String demoGroupName,
            int watchPercentage,
            String streamingServiceName,
            String eventName,
            int eventYear,
            ArrayList<DemographicGroup> demographicGroups,
            ArrayList<StreamingService> streamingServices,
            ArrayList<Event> events
    ) {
        // Check for existing demographic group.
        Optional<DemographicGroup> existingDemoGroup = demographicGroups
                .stream()
                .filter(o -> o.short_name.equals(demoGroupName))
                .findFirst();
        if (existingDemoGroup.isEmpty()) {
            if (verboseMode) { System.out.println("Cannot watch event -> no demographic group named " + demoGroupName); }
            return;
        }
        DemographicGroup demoGroup = existingDemoGroup.get();

        // Check for existing streaming service.
        Optional<StreamingService> existingService = streamingServices
                .stream()
                .filter(o -> o.short_name.equals(streamingServiceName))
                .findFirst();
        if (existingService.isEmpty()) {
            if (verboseMode) { System.out.println("Cannot watch event -> Streaming service does not exist."); }
            return;
        }
        StreamingService streamingService = existingService.get();

        // Check for existing event.
        Optional<Event> existingEvent = events
                .stream()
                .filter(o -> o.unique_id().equals(eventName + eventYear))
                .findFirst();
        if (existingEvent.isEmpty()) {
            if (verboseMode) { System.out.println("Cannot watch event -> Event does not exist."); }
            return;
        }
        Event event = existingEvent.get();

        int totalWatchers = (demoGroup.accounts * watchPercentage) / 100;

        if (event instanceof Movie) {
            Movie movie = (Movie) event;
            if (!streamingService.getMovies().contains(movie)) {
                if (verboseMode) { System.out.println("This streaming service is not offering this movie at the moment."); }
                return;
            }
            Integer numberOfExistingSubscribers = demoGroup.subscriptions.get(streamingService.short_name);
            if (numberOfExistingSubscribers != null) {
                // Some of the accounts have an existing subscription to this streaming service.
                int newWatchers = totalWatchers - numberOfExistingSubscribers;
                if (newWatchers > 0) {
                    demoGroup.subscriptions.put(streamingService.short_name, numberOfExistingSubscribers + newWatchers);
                    demoGroup.spend(newWatchers * streamingService.subscription_price);
                    streamingService.addSubscribers(newWatchers);
                }
            } else {
                // None of the accounts in this demographic group have subscribed to this streaming service before.
                demoGroup.subscriptions.put(streamingService.short_name, totalWatchers);
                demoGroup.spend(totalWatchers * streamingService.subscription_price);
                streamingService.addSubscribers(totalWatchers);
            }
        } else if (event instanceof PayPerView) {
            PayPerView payPerView = (PayPerView) event;
            if (!streamingService.getPayPerViews().contains(payPerView)) {
                if (verboseMode) { System.out.println("This streaming service is not offering this PayPerView at the moment."); }
                return;
            }
            int viewingPrice = payPerView.viewingPrices.get(streamingService.short_name);
            streamingService.addPPVRevenue(totalWatchers * viewingPrice);
            demoGroup.spend(totalWatchers * viewingPrice);
        }
    }

    public void next_month(Boolean verboseMode, State state) {
        if (verboseMode) { System.out.println("next_month_acknowledged"); }

        // Update the current timestamp
        if (state.monthTimeStamp == 12) { state.yearTimeStamp++; }
        state.monthTimeStamp = (state.monthTimeStamp % 12) + 1;

        state.demographicGroups.forEach(DemographicGroup::advanceMonth);
        state.streamingServices.forEach(StreamingService::advanceMonth);
        state.studios.forEach(Studio::advanceMonth);
        state.offers.clear();
    }

    public void display_demo(Boolean verboseMode, String demoGroupName, ArrayList<DemographicGroup> demographicGroups) {
        // Check for existing demographic group.
        Optional<DemographicGroup> existingDemoGroup = demographicGroups
                .stream()
                .filter(o -> o.short_name.equals(demoGroupName))
                .findFirst();
        if (existingDemoGroup.isEmpty()) {
            if (verboseMode) { System.out.println("Cannot display demo -> no demographic group named " + demoGroupName); }
            return;
        }
        existingDemoGroup.get().print();
    }

    public void display_events(Boolean verboseMode, ArrayList<Event> events) {
        if (verboseMode) { System.out.println("display_events_acknowledged"); }
        events.forEach(Event::print);
    }

    public void display_stream(Boolean verboseMode, String streamingServiceName, ArrayList<StreamingService> streamingServices) {
        // Check for existing streaming service.
        Optional<StreamingService> existingStreamingService = streamingServices
                .stream()
                .filter(o -> o.short_name.equals(streamingServiceName))
                .findFirst();
        if (existingStreamingService.isEmpty()) {
            if (verboseMode) { System.out.println("Cannot display stream -> no streaming service named " + streamingServiceName); }
            return;
        }
        existingStreamingService.get().print();
    }

    public void display_studio(Boolean verboseMode, String studioName, ArrayList<Studio> studios) {
        // Check for existing studio.
        Optional<Studio> existingStudio = studios
                .stream()
                .filter(o -> o.short_name.equals(studioName))
                .findFirst();
        if (existingStudio.isEmpty()) {
            if (verboseMode) { System.out.println("Cannot display studio -> no studio named " + studioName); }
            return;
        }
        existingStudio.get().print();
    }

    public void display_offers(Boolean verboseMode, ArrayList<String> offers) {
        if (verboseMode) { System.out.println("display_offers_acknowledged"); }
        offers.forEach(System.out::println);
    }

    public void display_time(Boolean verboseMode, int monthTimeStamp, int yearTimeStamp) {
        if (verboseMode) { System.out.println("display_time_acknowledged"); }
        System.out.println("time," + monthTimeStamp + "," + yearTimeStamp);
    }
}
