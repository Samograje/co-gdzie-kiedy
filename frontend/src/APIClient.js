import {Platform} from 'react-native';

const baseUrl = 'https://dry-wildwood-77221.herokuapp.com';

const request = (url, options) => {
  let finalUrl;

  if (Platform.OS === 'web') {
    finalUrl = url;
  }

  if (Platform.OS === 'android') {
    finalUrl = `${baseUrl}${url}`;
  }

  return fetch(finalUrl, options);
};

export default request;
