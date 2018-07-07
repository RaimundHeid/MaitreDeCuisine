let images = {};

function buildUploadRequestBody() {
  const body = Object.entries(images).map(([name, obj]) => ({
    id: name,
    mimeType: "image",
    content: obj.content,
    recognizedObjects: obj.recognizedObjects
  }));
  return {
    images: body
  };
}

function searchRecipesClickHandler() {
  showLoadingMessage();
  fetch("v1/recipes", {
    body: JSON.stringify(buildUploadRequestBody()),
    headers: {
      "content-type": "application/json"
    },
    method: "POST"
  })
    .then(response => response.json())
    .then(response => {
      console.log("Got recipies ", response);
      renderRecipes(response.recipes);
    })
    .catch(err => console.log("Got error ", error));
}

document.getElementById("searchRecipesButton").addEventListener("click", () => {
  searchRecipesClickHandler();
});

document.getElementById("resetButton").addEventListener("click", () => {
  images = {};
  renderPreviewImages();
  renderRecipes([]);
});

const landingarea = document.getElementById("landingarea");
landingarea.addEventListener("dragenter", dragenter, false);
landingarea.addEventListener("dragover", dragover, false);
landingarea.addEventListener("drop", drop, false);
landingarea.addEventListener("dragleave", dragleave, false);

function dragenter(e) {
  e.stopPropagation();
  e.preventDefault();
}

function dragleave(e) {
  e.stopPropagation();
  e.preventDefault();
  landingarea.classList.remove("drop");
}

function dragover(e) {
  e.stopPropagation();
  e.preventDefault();
  landingarea.classList.add("drop");
}

function drop(e) {
  e.stopPropagation();
  e.preventDefault();
  landingarea.classList.remove("drop");

  var dt = e.dataTransfer;
  var files = dt.files;

  handleFiles(files);
}

function handleFiles(files) {
  for (file of files) {
    if (file.type.startsWith("image/")) {
      const reader = new FileReader();
      reader.onload = (function(theFile) {
        return function(e) {
          const dataUrl = e.target.result;
          const base64String = dataUrl.replace(/data:.*;base64,(.*)/, "$1");
          images[theFile.name] = {
            content: base64String,
            dataUrl: dataUrl
          };

          renderPreviewImages();
        };
      })(file);

      reader.readAsDataURL(file);
    }
  }
}

function renderPreviewImages() {
  const imageEntries = Object.entries(images);
  const preview = document.getElementById("preview");
  if (!imageEntries.length) {
    preview.innerHTML = "<p>Upload some images</p>";
  } else {
    preview.innerHTML = "";

    const container = document.createElement("div");
    container.classList.add("d-flex");
    container.classList.add("flex-wrap");
    preview.appendChild(container);

    imageEntries.forEach(([name, imageObj]) => {
      const img = document.createElement("img");
      img.src = imageObj.dataUrl;
      img.classList.add("p-3");
      img.classList.add("w-25");
      container.appendChild(img);
    });
  }
}

function renderRecipes(recipes) {
  console.log("renderRecipes called with ", recipes);
  const recipesArea = document.getElementById("recipes");
  recipesArea.innerHTML = "";

  const group = document.createElement("div");
  group.classList.add("d-flex");
  group.classList.add("flex-wrap");
  recipesArea.appendChild(group);
  recipes.forEach((recipe, index) => {
    group.appendChild(renderRecipe(recipe));
  });
}

function renderRecipe(recipe) {
  const card = document.createElement("div");
  card.classList.add("card");
  card.classList.add("w-25");
  card.classList.add("p-3");

  const image = document.createElement("img");
  image.src = recipe.imageUrl;
  image.classList.add("card-image-top");
  image.classList.add("img-fluid");
  image.alt = recipe.label;
  card.appendChild(image);

  const cardBody = document.createElement("div");
  cardBody.classList.add("card-body");
  card.appendChild(cardBody);

  const cardTitle = document.createElement("h5");
  cardTitle.classList.add("card-title");
  cardTitle.innerHTML = recipe.label;
  cardBody.appendChild(cardTitle);

  const cardText = document.createElement("p");
  cardText.classList.add("card-text");
  cardBody.appendChild(cardText);

  const ingredientsList = document.createElement("ul");
  cardText.appendChild(ingredientsList);

  for (ingredient of recipe.ingredientLines) {
    const ingredientItem = document.createElement("li");
    ingredientItem.innerHTML = ingredient;
    ingredientsList.appendChild(ingredientItem);
  }

  return card;
}

function showLoadingMessage() {
  const recipesArea = document.getElementById("recipes");
  recipesArea.innerHTML = "";
  const content = document.createElement("div");
  content.classList.add("d-flex");
  content.classList.add("flex-column");
  content.classList.add("justify-content-center");
  content.classList.add("align-items-center");
  recipesArea.appendChild(content);
  const message = document.createElement("h3");
  message.innerHTML = "Thinking hard about food";
  content.appendChild(message);
  const spinner = document.createElement("div");
  spinner.classList.add("loader");
  content.appendChild(spinner);
}
