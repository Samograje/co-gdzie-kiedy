import {Platform} from 'react-native';

//const baseUrl = 'https://dry-wildwood-77221.herokuapp.com';
const baseUrl = 'http://localhost:8080';

const request = (url, options) => {
  let finalUrl;

  // TODO: docelowo trzeba to rozwiązać w inny sposób - bez proxy w package.json
  if (Platform.OS === 'web') {
    finalUrl = url;
  }
  if (Platform.OS === 'android') {
    finalUrl = `${baseUrl}${url}`;
  }

  // gdy nie ma podanych options
  if (!options) {
    return fetch(finalUrl);
  }

  const {filters, ...otherOptions} = options;

  // dodanie parametrów do urlu
  if (filters) {
    finalUrl = prepareUrl(finalUrl, {filters});
  }

  return fetch(finalUrl, otherOptions);
};

// dodaje parametry do urlu
const prepareUrl = (url, {filters}) => {
  let finalUrl = `${url}`;
  if (filters && Object.values(filters).filter((filter) => filter).length > 0) {
    finalUrl =`${url}?search=`;
  }
  Object.keys(filters).forEach((key) => {
    const value = filters[key];
    if (value) {
      finalUrl = `${finalUrl}${key}:${value},`;
    }
  });
  return finalUrl;
};

export default request;
