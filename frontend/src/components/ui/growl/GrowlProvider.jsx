import React from 'react';
import Growl from './Growl';

const GrowlContext = React.createContext();

class GrowlProvider extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      growlVisible: false,
      summary: null,
      detail: null,
    };
  }

  showGrowl = (summary, detail) => this.setState({
    growlVisible: true,
    summary,
    detail,
  });

  hideGrowl = () => this.setState({
    growlVisible: false,
    summary: null,
    detail: null,
  });

  showMessage = (summary, detail) => {
    if (this.state.growlVisible) {
      setTimeout(() => {}, 3000);
    }
    this.showGrowl(summary, detail);
    setTimeout(this.hideGrowl, 3000);
  };

  render() {
    const {children} = this.props;

    return (
      <>
        {this.state.growlVisible && (
          <Growl
            summary={this.state.summary}
            detail={this.state.detail}
          />
        )}
        <GrowlContext.Provider value={{showMessage: this.showMessage}}>
          {children}
        </GrowlContext.Provider>
      </>
    );
  }
}

const GrowlConsumer = GrowlContext.Consumer;

const withGrowl = Component => (props) => (
  <GrowlConsumer.Consumer>
    {({showMessage}) => (
      <Component
        {...props}
        showMessage={showMessage}
      />
    )}
  </GrowlConsumer.Consumer>
);

export {
  GrowlContext,
  GrowlProvider,
  GrowlConsumer,
  withGrowl,
};
