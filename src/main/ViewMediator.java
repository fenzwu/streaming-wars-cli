public class ViewMediator {

    private DemographicManager demographicManager;
    private ContentProvider contentProvider;

    ViewMediator(DemographicManager demographicManager, ContentProvider contentProvider) {
        this.demographicManager = demographicManager;
        this.contentProvider = contentProvider;
    }

    public void viewEvent(String demoName, int percentage, String serviceName, String eventName, int year) {

        Demographic demo = demographicManager.getDemographic(demoName);
        Service service = contentProvider.getService(serviceName);
        Event event = contentProvider.getEvent(eventName, year);

        // Ensure demographic, service, and event exists
        if (demo == null || service == null || event == null) {
            return;
        }

        // Ensure service offers event
        if (!service.offersEvent(event)) {
            return;
        }
        
        event.setToViewed();

        // Add view to demographic
        int revenue = 0;
        if(event.getType() == EventType.MOVIE) {
            // Mark the service as being used to watch a movie, which blocks the ability to change the subscription
            // price until the beginning of the next time period.
            service.markServiceUsed();
            revenue = demo.addMovieView(serviceName, service.getSubscriptionPrice(), percentage);
        } else if (event.getType() == EventType.PPV) {
            revenue = demo.addPPVView(service.getDirectory().getPPVPrice(event), percentage);
        }

        // Add ppv or subscription revenue to service
        service.addRevenue(revenue);

    }
}
