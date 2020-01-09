import {Platform} from 'react-native';
import routes from "./routes";

const withCustomRouting = (InputComponent) => {

  if (Platform.OS === 'web') {
    return ({match, history}) => {

      const push = (screenName, options) => {
        let path = routes[screenName].path;

        // zastępowanie parametrów urlu wartościami
        const regex = /:[^/]*/g;
        const results = path.match(regex);
        if (results) {
          results.forEach((result) => {
            if (result[result.length - 1] === '?') {
              // gdy parametr jest opcjonalny

              const name = result.substring(1, result.length - 1);
              if (options[name]) {
                // gdy został podany w options

                path = path.replace(result, options[name]);
              } else {
                path = path.replace(result, '');
              }
            } else {
              // gdy parametr jest wymagany

              const name = result.substring(1, result.length);
              path = path.replace(result, options[name]);
            }
          });
        }

        history.push(path);
      };

      const goBack = () => {
        history.goBack();
      };

      /*return (
        <InputComponent
          push={push}
          goBack={goBack}
          {...match.params}
        />
      );*/
    };
  }

  if (Platform.OS === 'android') {
    /* return ({navigation}) => (
       <InputComponent
         push={navigation.navigate}
         goBack={navigation.goBack}
         {...navigation.state.params}
       />
     );*/
  }
};

export default withCustomRouting;
