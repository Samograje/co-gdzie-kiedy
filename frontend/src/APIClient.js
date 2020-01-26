import {Platform} from 'react-native';

const baseUrl = 'https://dry-wildwood-77221.herokuapp.com';

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

  const {filters, searchType, ...otherOptions} = options;

  // dodanie parametrów do urlu
  finalUrl = prepareUrl(finalUrl, {filters, searchType});

  return fetch(finalUrl, otherOptions);
};

// dodaje parametry do urlu
const prepareUrl = (url, {filters, searchType}) => {
  let finalUrl = `${url}`;

  let filtersEnabled = false;
  if (filters) {
    filtersEnabled = Object.values(filters).filter((filter) => filter).length > 0;
  }

  if (filtersEnabled) {
    finalUrl = `${finalUrl}?`;
  }

  // dodanie parametru z filtrami
  if (filters && filtersEnabled) {
    finalUrl =`${finalUrl}search=`;
    Object.keys(filters).forEach((key) => {
      const value = filters[key];
      if (value) {
        finalUrl = `${finalUrl}${key}:${value},`;
      }
    });
    finalUrl = finalUrl.substring(0, finalUrl.length - 1);

    // dodanie parametru z typem wyszukiwania
    if (searchType) {
      finalUrl = `${finalUrl}&search-type=${searchType}`
    }
  }

  return finalUrl;
};

export default request;
