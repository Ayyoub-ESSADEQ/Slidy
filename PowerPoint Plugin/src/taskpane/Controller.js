export default class Controller {
  constructor() {}

  static goToPreviousSlide() {
    Office.context.document.goToByIdAsync(Office.Index.Previous, Office.GoToType.Index, function (asyncResult) {
      if (asyncResult.status == "failed") {
        this.showNotification("Error", asyncResult.error.message);
      }
    });
  }

  static goToNextSlide() {
    Office.context.document.goToByIdAsync(Office.Index.Next, Office.GoToType.Index, (asyncResult) => {
      if (asyncResult.status == "failed") {
        this.showNotification("Error", asyncResult.error.message);
      }
    });
  }

  static goToSlide(number) {
    Office.context.document.goToByIdAsync(number, Office.GoToType.Index, function (asyncResult) {
      if (asyncResult.status == "failed") {
        this.showNotification("Error", asyncResult.error.message);
      }
    });
  }

  showNotification(error, message) {
    // eslint-disable-next-line no-undef
    console.log(error, message);
  }
}
