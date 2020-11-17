public class ViewMediator {

    private UserManager userManager;
    private ContentProvider contentProvider;

    ViewMediator(UserManager userManager, ContentProvider contentProvider) {
        this.userManager = userManager;
        this.contentProvider = contentProvider;
    }

    public void viewEvent(String demoName, int percentage, String serviceName, String eventName, int year) {

        Demographic demo = userManager.getDemographic(demoName);
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

        // Add view to demographic
        int revenue = 0;
        if(event.getType() == EventType.MOVIE) {
            revenue = demo.addMovieView(serviceName, service.getSubscriptionPrice(), percentage);
        } else if (event.getType() == EventType.PPV) {
            revenue = demo.addPPVView(service.getDirectory().getPPVPrice(event), percentage);
        }

        // Add ppv or subscription revenue to service
        service.addRevenue(revenue);

    }
}
