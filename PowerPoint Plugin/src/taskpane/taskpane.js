/*
 * Copyright (c) Microsoft Corporation. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */

import { default as Controller } from "./Controller";

Office.onReady((info) => {
  if (info.host === Office.HostType.PowerPoint) {
    const ip = document.getElementById("ip");
    const btn = document.getElementById("btn");
    btn.addEventListener("click", () => {
      const server = new WebSocket(`wss://${ip.value}:8887`);
      const commands = {
        next: Controller.goToNextSlide,
        backward: Controller.goToPreviousSlide,
        jump: Controller.goToSlide,
      };

      server.onmessage = (message) => {
        const content = JSON.parse(message.data)?.content;
        const command = content?.command;
        const attributes = parseInt(content?.attributes);

        if (attributes !== undefined) commands[command](attributes);
        else commands[command]();
      };
    });
  }
});
